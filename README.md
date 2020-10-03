# EnchanterCore
Welcome to Enchanter Core! This is an API allowing people to easily add there own custom enchantments into the game. This API is FULLY customisable. The way in which the lore is added to the item is meant to look like vanilla by default, but this is fully overridable if so desired.

Version support
All versions after 1.8.9 are supported. If you use this API and you get the message saying that your version isn't supported, contact me on Discord (JetCobblestone#9979) and I'll add your version to the API.

# How to use?
This is an example of how to create an enchantment:
```java
private final static TagWrapper tagWrapper = TagManager.getInstance().getImplemenation();
private static CustomEnchantment myEnch;

public static void loadEnchantments() {
	force = new CustomEnchantment("MyEnchantment", 3, e -> {
		//Enchantment effect, where e is the event passed in from the listener
	});	
	
@EventHandler
public void onBlock (PlayerInteractEntityEvent event) {
	if (tagWrapper.checkTag(event.getPlayer().getItemInHand(), myEnch.getId())) {
		myEnch.trigger(event);
	};
}
```
That's all you need to do!

To add an enchantment to an item in game, you can use the command /enchanter enchant (Enchantment name) [level] To add an enchantment in the code you can use EnchantAdder.getInstance().getImplementation().addEnchant() To remove an enchantment to an item in game, you can use the command /enchanter remove (Enchantment name) To remove an enchantment in the code you can use EnchantAdder.getInstance().getImplementation().removeEnchant()

There is currenty no organic way to add enchantments and will have to be created by your own plugin.

The CustomEnchantment class should be safe to extend, though I haven't tested.

# Contact me
If you have any further questions on the functionality or capabilities, drop me a message on discord. If you want some additional functionality or any changes made, do the same. Discord: JetCobblestone#9979

# Lisence
You are free to use this code as you wish (*as long as you don't try to parse it as your own*), but creditting me is always appreciated :)
