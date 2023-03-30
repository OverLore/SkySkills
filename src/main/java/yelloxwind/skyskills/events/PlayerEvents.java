package yelloxwind.skyskills.events;

import net.coreprotect.CoreProtect;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import yelloxwind.skyskills.SkySkills;
import yelloxwind.skyskills.skill.PlayerSkillController;
import yelloxwind.skyskills.skill.Skill;

public class PlayerEvents implements Listener {
    SkySkills plugin;
    int tick = 0;

    public PlayerEvents(SkySkills plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        plugin.playersSkills.put(e.getPlayer().getUniqueId(), new PlayerSkillController(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        plugin.playersSkills.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e)
    {
        Entity entity = e.getEntity();
        Entity killer = e.getEntity().getKiller();

        if (!(killer instanceof Player))
            return;

        Player player = (Player)killer;

        if (entity instanceof Animals) {
            plugin.playersSkills.get(player.getUniqueId()).addXp(Skill.SkillType.Hunting, 1333);
            plugin.playersSkills.get(player.getUniqueId()).addXp(Skill.SkillType.Mining, 134);
            plugin.playersSkills.get(player.getUniqueId()).addXp(Skill.SkillType.WoodCutting, 11);
            plugin.playersSkills.get(player.getUniqueId()).addXp(Skill.SkillType.Farming, 15667);
        }

        if (entity instanceof Monster)
            plugin.playersSkills.get(player.getUniqueId()).addXp(Skill.SkillType.Hunting, 2);
    }

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e)
    {
        if (e.getPlayer() == null)
            return;

        if (CoreProtect.getInstance().getAPI().blockLookup(e.getBlock(), (int)(System.currentTimeMillis() / 1000L)).size() > 0)
            return;

        switch (e.getBlock().getType()) {
            case ACACIA_LOG:
            case BIRCH_LOG:
            case OAK_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case MANGROVE_LOG:
            case SPRUCE_LOG:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.WoodCutting, 1);
                break;

            case STONE:
            case COBBLESTONE:
            case DIORITE:
            case ANDESITE:
            case GRANITE:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Mining, 1);
                break;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case NETHER_QUARTZ_ORE:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Mining, 2);
                break;
            case NETHER_GOLD_ORE:
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Mining, 3);
                break;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Mining, 5);
                break;
            case DEEPSLATE_DIAMOND_ORE:
            case DIAMOND_ORE:
            case DEEPSLATE_EMERALD_ORE:
            case EMERALD_ORE:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Mining, 10);
                break;

            case CARROTS:
            case POTATOES:
            case WHEAT:
            case BEETROOTS:
            case SWEET_BERRIES:
            case GLOW_BERRIES:
            case COCOA:
            case NETHER_WART:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case CACTUS:
            case SUGAR_CANE:
            case CRIMSON_FUNGUS:
            case WARPED_FUNGUS:
            case BAMBOO:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Farming, 1);
                break;
            case PUMPKIN:
            case MELON:
                plugin.playersSkills.get(e.getPlayer().getUniqueId()).addXp(Skill.SkillType.Farming, 2);
                break;
        }
    }
}
