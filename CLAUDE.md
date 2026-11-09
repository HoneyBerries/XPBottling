# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

XP Bottling is a Paper/Folia/Purpur plugin (Minecraft 26.3, Java 25). Right-clicking an enchanting table with a glass bottle costs 7 XP and gives an experience bottle. Single Maven module, one class: `me.honeyberries.xpbottling.XPBottling` (plugin + listener). No tests, no linter.

## Commands

- Build: `mvn clean package` → `target/xp-bottling-<version>.jar` (maven-shade runs; `target/original-*.jar` is the unshaded one).
- No run target; test by dropping the jar into a Paper server's `plugins/` folder.

## Notes

- `plugin.yml` is Maven-filtered, but `version` and `api-version` are set by hand. Keep its version in sync with `pom.xml`.
- The XP check in `onPlayerInteract` (`getTotalExperience() < cost && getLevel() < 1`) is intentionally left as the author wrote it.

## Releases

Pushing to `main` triggers `.github/workflows/publish.yml`. It reads the version from `pom.xml`, skips if a GitHub release `v<version>` already exists, otherwise publishes to Modrinth (project `Tb1HV98q`, slug `xp-bottling`) with `Kir-Antipov/mc-publish@v3`, then creates the GitHub release. Bump the version in **both** `pom.xml` and `plugin.yml` to release. The Modrinth step needs a `MODRINTH_TOKEN` repo secret; without it the action only warns and Modrinth is silently skipped. Licensed GPL-3.0.
