package yelloxwind.skyskills.bar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import yelloxwind.skyskills.SkySkills;

public class SkillBar {
    public BossBar bar;
    public int min;
    public int max;
    public int current;
    public Player owner;
    int tick;
    BarColor defaultColor;

    public SkillBar(Player player, BarColor color)
    {
        owner = player;
        defaultColor = color;

        bar = Bukkit.createBossBar("", color, BarStyle.SOLID);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (tick >= 0)
                    tick++;

                if (tick >= 5) {
                    bar.removeAll();
                    tick = -1;
                }
            }
        }.runTaskTimer(SkySkills.Instance, 0L, 20L);
    }

    public void update(String title, BarStyle style, int min, int max, int current, boolean isFull)
    {
        bar.setTitle(title);
        bar.setStyle(style);
        bar.setColor(isFull ? BarColor.BLUE : defaultColor);

        this.min = min;
        this.max = max;
        this.current = current;

        bar.setProgress((float)(current - min) / (max - min));
        bar.addPlayer(owner);

        tick = 0;
    }
}
