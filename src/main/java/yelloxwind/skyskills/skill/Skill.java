package yelloxwind.skyskills.skill;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import skychat.skychat.SkyChat;
import yelloxwind.skyskills.SkySkills;
import yelloxwind.skyskills.bar.SkillBar;

public class Skill {
    public enum SkillType
    {
        Farming,
        Mining,
        Hunting,
        WoodCutting
    }

    public String name;
    public BarStyle style;
    public Player owner;
    public int xp = 0;
    public int level = 1;
    public int prestige;
    public int min = 0;
    public int max = 10;
    int increaseSpeed = 1;
    int addedXp = 5;
    int toAdd = 0;
    SkillBar bar;

    public Skill(Player owner, String name, SkillBar bar)
    {
        this.owner = owner;
        this.name = name;
        this.bar = bar;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (toAdd > 0) {
                    bar.update(ChatColor.WHITE + name + ChatColor.WHITE + ChatColor.BOLD + " Lv." + level + ChatColor.DARK_GRAY + " - " + ChatColor.RED + xp + "/" + max, getBarStyle(), min, max, xp, false);

                    owner.sendActionBar(Component.text(ChatColor.RED + "" + ChatColor.BOLD + "【" + ChatColor.GOLD + "Compétences" + ChatColor.RED + ChatColor.BOLD + "】" + ChatColor.YELLOW + "→  " + ChatColor.GOLD + "+" + toAdd + ChatColor.RED + " Exp " + ChatColor.GOLD + "+" + toAdd + ChatColor.WHITE + SkySkills.Instance.skyFont.getCharacter("money")));

                    toAdd = 0;
                }
            }
        }.runTaskTimer(SkySkills.Instance, 0L, 12L);
    }

    BarStyle getBarStyle()
    {
        if (level < 20)
            return BarStyle.SOLID;
        else if (level < 40)
            return BarStyle.SEGMENTED_6;
        else if (level < 60)
            return BarStyle.SEGMENTED_10;
        else if (level < 80)
            return BarStyle.SEGMENTED_12;
        else
            return BarStyle.SEGMENTED_20;
    }

    public void addXp(int amount)
    {
        if (prestige == 10)
            return;

        if (level == 100 && xp >= max)
        {
            xp = max;
            bar.update(ChatColor.WHITE + name + ChatColor.WHITE + ChatColor.BOLD + " Lv." + level + ChatColor.DARK_GRAY + " - " + ChatColor.RED + xp + "/" + max, getBarStyle(), min, max, xp, true);

            return;
        }

        //TODO: Check for double xp
        xp += amount;

        while (xp >= max)
        {
            xp -= (max - min);
            level++;
            max = 10 + (level-1) * addedXp;

            if (level >= 100 && xp >= max)
            {
                xp = max;
                level = 100;
                bar.update(ChatColor.WHITE + name + ChatColor.WHITE + ChatColor.BOLD + " Lv." + level + ChatColor.DARK_GRAY + " - " + ChatColor.RED + xp + "/" + max, getBarStyle(), min, max, xp, true);

                return;
            }
        }

        toAdd += amount;
    }
}
