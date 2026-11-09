# XP Bottling

[![build](https://github.com/HoneyBerries/XPBottling/actions/workflows/build.yml/badge.svg)](https://github.com/HoneyBerries/XPBottling/actions/workflows/build.yml)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

A lightweight Paper plugin for Minecraft **26.3** that lets players store their experience into experience bottles.

Available on [Modrinth](https://modrinth.com/plugin/xp-bottling).

## How it works

1. Hold a glass bottle in your main hand.
2. Right-click an enchanting table.
3. The plugin takes **7 XP points** (configurable), consumes the glass bottle and gives you an experience bottle, with a sound effect.

7 XP is the average a thrown experience bottle gives back (it drops 3 to 11 points), so bottling is roughly lossless.

Players without the permission or without enough XP get a chat message instead. The enchanting table menu does not open while you hold a glass bottle.

## Compatibility

| Requirement | Version |
|-------------|---------|
| Minecraft | 26.3 |
| Server software | Paper and forks (Folia, Purpur) |
| Java | 25 |

## Permissions

| Permission | Default | Description |
|------------|---------|-------------|
| `xpbottling.use` | everyone | Bottle XP at an enchanting table |

## Configuration

`plugins/XPBottling/config.yml` is created on first start. Restart the server to apply changes.

| Option | Default | Description |
|--------|---------|-------------|
| `xp-cost` | `7` | XP points taken per bottle (minimum 1) |

## Installation

1. Download the latest jar from [Modrinth](https://modrinth.com/plugin/xp-bottling) or [GitHub Releases](https://github.com/HoneyBerries/XPBottling/releases).
2. Put it in your server's `plugins` folder.
3. Start the server.

## Building from source

Requires JDK 25 and Maven.

```sh
git clone https://github.com/HoneyBerries/XPBottling.git
cd XPBottling
mvn clean package
```

The jar is written to `target/xp-bottling-<version>.jar`.

## Releases

Pushing to `main` runs the [publish workflow](.github/workflows/publish.yml). If the version in `pom.xml` has not been released yet, it builds the plugin, publishes it to Modrinth and creates a GitHub release. Bump the version in `pom.xml` and `plugin.yml` to cut a new release.

## Contributing

Issues and pull requests are welcome.

## License

XP Bottling is licensed under the [GNU General Public License v3.0](LICENSE).
