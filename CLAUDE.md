# Product Engineer — Keep Trim

Sibling mod of the Dungeon Train family (AIN / AIS / PlayerMob / ECP / TE / **Keep Trim**).

## Quick Reference

| | |
|---|---|
| Mod id | `keeptrim` |
| Group | `games.brennan.keeptrim` |
| Version | `gradle.properties` → `mod_version` |
| Build | `./gradlew build` |
| Tests | `./gradlew :common:test` |
| Key jars | `{fabric,forge,neoforge}/build/libs/keeptrim-<loader>-<v>.jar` |
| Release | `gh workflow run release.yml -f tag=v<version>` (creates the tag; never tag manually) |
| Repo | `bh679/keeptrim-mc` |

## What it does

Vanilla's crafting-grid repair (`RepairItemRecipe.assemble`) builds a fresh stack and
keeps only durability + curses, so an armor trim is lost. Keep Trim re-applies the trim of
the **left-most, then top-most** input to the result; if that input has no trim the result
stays untrimmed (the other input's trim is never used). The anvil already keeps the left item's trim (result = copy of left)
and is deliberately untouched.

## Structure

- `common/` — all logic.
  - `repair/RepairTrimPicker` — pure column-major slot picker (no MC types; unit-tested).
  - `mixin/RepairItemRecipeMixin` — `@Inject` at RETURN of `assemble`, sets `DataComponents.TRIM`
    on the (mutable) returned stack. The only mixin.
- `fabric/`, `forge/`, `neoforge/` — thin entrypoints (`KeepTrim.init()`). No loader events,
  no config, no registries, no networking.

## Standards

SemVer in `gradle.properties`: PATCH every commit, MINOR on release. bh679 Gate
workflow applies (see `~/.claude/` rules/playbooks). Releases only via release.yml
dispatch — it creates the tag, GitHub Release, and publishes to Modrinth/CurseForge
when `MODRINTH_PROJECT_ID`/`CURSEFORGE_PROJECT_ID` vars + tokens are set.

Dungeon Train consumes the **neoforge** jar via its shared `bh679` Ivy repo:
asset name MUST stay `keeptrim-neoforge-<v>.jar` (flat, no `+mc` suffix).
