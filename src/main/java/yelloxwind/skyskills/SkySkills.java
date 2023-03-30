package yelloxwind.skyskills;

import org.bukkit.plugin.java.JavaPlugin;
import skychat.skychat.SkyChat;
import skyfont.skyfont.SkyFont;
import yelloxwind.skyskills.database.MySQLDatabase;
import yelloxwind.skyskills.events.PlayerEvents;
import yelloxwind.skyskills.skill.PlayerSkillController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SkySkills extends JavaPlugin {
    public static SkySkills Instance;
    public MySQLDatabase db;
    public SkyChat skyChat;
    public SkyFont skyFont;

    public Map<UUID, PlayerSkillController> playersSkills = new HashMap<>();

    public void setupSkillsTable()
    {
        ResultSet resultSet = db.executeQuery("SHOW TABLES LIKE 'skills'");
        try {
            if (resultSet.next()) {
                return;
            }

            getLogger().info("Missing table islands in database, creating it...");

            db.executeUpdate("CREATE TABLE `skyblock`.`skills` (`uuid` VARCHAR(36) NULL , " +
                    "`farming` VARCHAR(12) NOT NULL , `hunting` VARCHAR(12) NOT NULL , `mining` VARCHAR(12) NOT NULL , " +
                    "`woodcutting` VARCHAR(12) NOT NULL , PRIMARY KEY (`uuid`(36))) ENGINE = InnoDB;");

            getLogger().info("Created !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        Instance = this;

        skyChat = SkyChat.getPlugin();
        skyFont = SkyFont.getPlugin();

        db = new MySQLDatabase("127.0.0.1", "skyblock", "3306", "root", "");
        setupSkillsTable();

        getLogger().info("SkySkills started");

        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("SkySkills stopped");
    }
}
