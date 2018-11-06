/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.games.ec.creature.ability;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kp.games.ec.battle.cmd.BattleCommandManager;
import kp.games.ec.creature.Creature;
import kp.games.ec.creature.attributes.Element;
import kp.games.ec.creature.attributes.StatId;
import kp.games.ec.creature.attributes.StatusId;
import kp.games.ec.utils.RNG;
import kp.games.ec.utils.Utils;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Asus
 */
public final class AbilityEffect
{
    public static final AbilityEffect INVALID_EFFECT = new AbilityEffect();
    
    private final EffectModel model;
    private final int[] flags;
    private String desc;
    
    private AbilityEffect(EffectModel model)
    {
        this.model = model;
        flags = new int[model.flags.length];
    }
    private AbilityEffect() { this(new EffectModel()); }
    
    public static final AbilityEffect createEffect(int id)
    {
        EffectModel model = MODELS.getOrDefault(id, null);
        return model == null ? INVALID_EFFECT : new AbilityEffect(model);
    }
    
    public final int getId() { return model.id; }
    
    public final void setFlag(int index, int value) { this.flags[index] = value; desc = null; }
    public final void setFlag(int index, Enum<?> e) { this.flags[index] = e.ordinal(); desc = null; }
    
    public final int getFlag(int index) { return flags[index]; }
    
    public final int getFlagCount() { return model.flags.length; }
    public final int getFlagType(int index) { return model.flags[index]; }
    
    public final String getDescription() { return desc == null ? desc = model.generateDescription(this) : desc; }
    
    
    
    
    public final void apply(Creature user, Creature target, RNG rng, BattleCommandManager bcm)
    {
        switch(model.id)
        {
            case 0: {
                int damage = ApplyEffect.physicFormula(user, targetFlag(0, user, target), rng, 0, 0, flags[1], elementFlag(2));
                ApplyEffect.applyDamage(user, target, bcm, damage);
            } break;
            case 1: {
                int damage = ApplyEffect.energyFormula(user, targetFlag(0, user, target), rng, 0, 0, flags[1], elementFlag(2));
                ApplyEffect.applyDamage(user, target, bcm, damage);
            } break;
            case 2: {
                int cure = ApplyEffect.cureFormula(user, targetFlag(0, user, target), rng, 0, flags[1], Element.POSITIVE);
                ApplyEffect.applyDamage(user, target, bcm, cure);
            } break;
            case 3: {
                int damage = ApplyEffect.healthPartFormula(user, targetFlag(0, user, target), rng, flags[1], elementFlag(2));
                ApplyEffect.applyDamage(user, target, bcm, damage);
            } break;
            case 4: {
                StatusId status = statusFlag(2);
                if(status != null && rng.d100(flags[1]))
                {
                    target = targetFlag(flags[0], user, target);
                    target.getStatus().applyStatus(status, rng, bcm);
                }
            } break;
        }
    }
    
    
    public final int AI_Score(Creature user, Creature target, RNG rng)
    {
        return 0;
    }
    
    
    private Element elementFlag(int index)
    {
        int value = flags[index];
        return value < 0 || value >= Element.count() ? null : Element.fromValue(value);
    }
    
    private StatId statFlag(int index)
    {
        int value = flags[index];
        return value < 0 || value >= StatId.count() ? null : StatId.fromValue(value);
    }
    
    private StatusId statusFlag(int index)
    {
        int value = flags[index];
        return value < 0 || value >= StatusId.count() ? null : StatusId.fromValue(value);
    }
    
    private Creature targetFlag(int index, Creature user, Creature target)
    {
        return flags[index] == 0 ? target : user;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static final HashMap<Integer, EffectModel> MODELS = loadModels();
    
    private static final class EffectModel
    {
        private final int id;
        private final int[] flags;
        private final LinkedList<DescNode> desc;
        
        private EffectModel(int id, Map map)
        {
            this.id = id;
            List lflags = (List) map.getOrDefault("flags", Collections.EMPTY_LIST);
            
            this.flags = new int[lflags.size()];
            int count = 0;
            for(Object obj : lflags)
                flags[count++] = ((Number) obj).intValue();
            
            this.desc = new LinkedList<>();
            String sdesc = (String) map.getOrDefault("description", "");
            buildDescription(sdesc);
        }
        private EffectModel()
        {
            this.id = -1;
            this.flags = new int[0];
            this.desc = new LinkedList<>();
        }
        
        public final String generateDescription(AbilityEffect effect)
        {
            if(desc.isEmpty())
                return "";
            
            StringBuilder sb = new StringBuilder();
            for(DescNode node : desc)
                sb.append(node.generateString(this, effect));
            return sb.toString();
        }
        
        private void buildDescription(String base)
        {
            StringBuilder sb = new StringBuilder();
            char[] chars = base.toCharArray();
            for(int i=0;i<chars.length;i++)
            {
                char c;
                switch(c = chars[i])
                {
                    case '$':
                        if(sb.length() > 0)
                        {
                            desc.add(new TextDescNode(sb.toString()));
                            sb.delete(0, sb.length());
                        }
                        if(i + 1 >= chars.length)
                            break;
                        for(i++; i < chars.length; i++)
                        {
                            if(!Character.isDigit(c = chars[i]))
                            {
                                i--;
                                break;
                            }
                            sb.append(c);
                        }
                        if(sb.length() > 0)
                        {
                            desc.add(new FlagDescNode(Integer.parseInt(sb.toString())));
                            sb.delete(0, sb.length());
                        }
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
            
            if(sb.length() > 0)
                desc.add(new TextDescNode(sb.toString()));
        }
    }
    
    private static abstract class DescNode
    {
        public abstract String generateString(EffectModel model, AbilityEffect effect);
    }
    
    private static final class TextDescNode extends DescNode
    {
        private final String text;
        private TextDescNode(String text) { this.text = text == null ? "" : text; }
        @Override public final String generateString(EffectModel model, AbilityEffect effect) { return text; }
    }
    
    private static final class FlagDescNode extends DescNode
    {
        private final int flagId;
        private FlagDescNode(int flagId) { this.flagId = flagId < 0 ? 0 : flagId; }
        @Override public final String generateString(EffectModel model, AbilityEffect effect)
        {
            if(flagId >= model.flags.length)
                return "";
            switch(model.flags[flagId])
            {
                default:
                case FLAG_TYPE_INTEGER: return Integer.toString(effect.flags[flagId]);
                case FLAG_TYPE_DEC_PERCENTAGE: return effect.flags[flagId] + "%";
                case FLAG_TYPE_BYTE_PERCENTAGE: return Utils.decimalValueToString(effect.flags[flagId] / 2.56f) + "%";
                case FLAG_TYPE_POWER: return Integer.toString(effect.flags[flagId]);
                case FLAG_TYPE_TARGET: return effect.flags[flagId] != 0 ? "Usuario" : "Objetivo";
                case FLAG_TYPE_STAT: return StatId.fromValue(effect.flags[flagId]).getName();
                case FLAG_TYPE_ELEMENT: return Element.fromValue(effect.flags[flagId]).getName();
                case FLAG_TYPE_STATUS: return StatusId.fromValue(effect.flags[flagId]).getName();
            }
        }
    }
    
    public static final int FLAG_TYPE_INTEGER = 0;
    public static final int FLAG_TYPE_DEC_PERCENTAGE = 1;
    public static final int FLAG_TYPE_BYTE_PERCENTAGE = 2;
    public static final int FLAG_TYPE_POWER = 3;
    public static final int FLAG_TYPE_TARGET = 4;
    public static final int FLAG_TYPE_STAT = 5;
    public static final int FLAG_TYPE_ELEMENT = 6;
    public static final int FLAG_TYPE_STATUS = 7;
    
    
    private static HashMap<Integer, EffectModel> loadModels()
    {
        HashMap<Integer, EffectModel> models = new HashMap<>();
        
        try
        {
            Yaml yaml = new Yaml();
            Map root = yaml.load(Utils.getInnerInputStream("/kp/games/ec/creature/ability/AbilityEffectConfig.yml"));
            Map<?, ?> base = (Map<?, ?>) root.getOrDefault("effects", Collections.EMPTY_MAP);
            for(Map.Entry<?, ?> e : base.entrySet())
            {
                Object oKey = e.getKey();
                Object oModel = e.getValue();
                if(!(oKey instanceof Integer) || !(oModel instanceof Map))
                    continue;
                Integer id = (Integer) oKey;
                models.put(id, new EffectModel(id, (Map) oModel));
            }
        }
        catch(Throwable ex) { ex.printStackTrace(System.err); }
        
        return models;
    }
}
