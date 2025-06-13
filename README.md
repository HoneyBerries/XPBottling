# XP Bottling Plugin

XP Bottling is a lightweight Minecraft plugin that allows players to store their experience points (XP) into bottles for later use. This plugin is designed for Bukkit/Spigot servers and provides a simple yet useful feature for managing XP.

## Features

- **XP Bottling**: Players can right-click an enchanting table with a glass bottle to convert their XP into an experience bottle.
- **Permission-Based Access**: Only players with the `xpbottling.use` permission can use the XP bottling feature.
- **Custom XP Cost**: Bottling XP costs 7 experience points per bottle. (Note: 7 XP points is the average amount of experience an experience bottle will give when thrown, as it drops a random amount of XP between 3 and 11 points.)
- **Sound Effects**: Plays a custom sound when XP is bottled for an immersive experience.
- **Error Messages**: Players are notified if they lack permissions, XP, or the required items.

## How It Works

1. Hold a glass bottle in your main hand.
2. Right-click an enchanting table.
3. If you have at least 7 XP points, the plugin will:
   - Deduct 7 XP points.
   - Consume one glass bottle.
   - Add an experience bottle to your inventory.
   - Play a sound effect.

If you don't meet the requirements, the plugin will notify you with a message.

## Permissions

- `xpbottling.use`: Grants permission to use the XP bottling feature.

## Installation

1. Download the plugin `.jar` file.
2. Place the `.jar` file in your server's `plugins` folder.
3. Restart or reload your server.
4. Ensure players have the `xpbottling.use` permission to use the plugin.

## Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and test them thoroughly.
4. Submit a pull request with a detailed description of your changes.

## Issues

If you encounter any bugs or have feature requests, please open an issue in the [GitHub Issues](https://github.com/HoneyBerries/xPBottling/issues) section.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgments

- Built using the Bukkit API.
- Inspired by the need for better XP management in Minecraft.

Enjoy the plugin and happy bottling!
