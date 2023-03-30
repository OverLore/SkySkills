package yelloxwind.skyskills.skill;

import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import yelloxwind.skyskills.bar.SkillBar;

import java.util.HashMap;
import java.util.Map;

public class PlayerSkillController {
    Player owner;
    Map<Skill.SkillType, Skill> skills = new HashMap<>();

    public PlayerSkillController(Player player) {
        owner = player;

        skills.put(Skill.SkillType.Farming, new Skill(owner, "丑", new SkillBar(owner, BarColor.GREEN)));
        skills.put(Skill.SkillType.Hunting, new Skill(owner, "专", new SkillBar(owner, BarColor.RED)));
        skills.put(Skill.SkillType.WoodCutting, new Skill(owner, "丒", new SkillBar(owner, BarColor.YELLOW)));
        skills.put(Skill.SkillType.Mining, new Skill(owner, "丐", new SkillBar(owner, BarColor.WHITE)));
    }

    public void addXp(Skill.SkillType type, int amount)
    {
        skills.get(type).addXp(amount);
    }
}
