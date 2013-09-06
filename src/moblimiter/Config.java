package moblimiter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class Config {

	public Config(MobLimiter plugin)
	{
		configfile = new File(plugin.getDataFolder(),"config.yml");
	}
	
	
	private HashMap<EntityType,Integer> enttypeidlimit = new HashMap<EntityType,Integer>();
	public int getCreatureSpawnLimit(EntityType etype)
	{
		if (enttypeidlimit.containsKey(etype))
		{
			return enttypeidlimit.get(etype).intValue();
		} else
		{
			return -1;
		}
	}
	
	private File configfile;
	public void loadConfig()
	{
		FileConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		enttypeidlimit.clear();
		ConfigurationSection cs = config.getConfigurationSection("");
		if (cs != null)
		{
			for (String type : cs.getKeys(false))
			{
				enttypeidlimit.put(EntityType.fromName(type), config.getInt(type));
			}
		}
		saveConfig();
	}
	private void saveConfig()
	{
		FileConfiguration config = new YamlConfiguration();
		for (EntityType etype : enttypeidlimit.keySet())
		{
			config.set(etype.getName(), enttypeidlimit.get(etype));
		}
		try {config.save(configfile);} catch (IOException e) {}
	}
	
	
}