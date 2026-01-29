# Steam 'n' Rails - Build Instructions

## Build Location

**Build on yogsothoth** - cthonian does not have Java 21 installed.

```bash
ssh yogsothoth
cd /home/critic/minecraft/modding/Railway-1.21.1
./gradlew build
```

## Deploy to Test Server

```bash
# Stop server, deploy, restart
tmux send-keys -t mc-test 'stop' Enter
sleep 10
cp build/libs/railways-0.1.0+neoforge-mc1.21.1-local.jar /home/critic/minecraft/neoforge.test/mods/
tmux send-keys -t mc-test './run.sh' Enter
```

Or if tmux session is gone:
```bash
cp build/libs/railways-0.1.0+neoforge-mc1.21.1-local.jar /home/critic/minecraft/neoforge.test/mods/
cd /home/critic/minecraft/neoforge.test
tmux new-session -d -s mc-test './run.sh'
```

## Source Directories

- `src/main/java` - Java/Kotlin source code
- `src/main/resources` - Textures, core configs
- `src/resources` - **Hand-crafted models** (takes priority over generated)
- `src/generated/resources` - Datagen output

**Important:** `src/resources` contains manually edited track models (like phantom narrow/wide gauge segment models). These override any auto-generated models.

## Syncing Changes from cthonian

If editing on cthonian, sync to yogsothoth before building:
```bash
# From cthonian
rsync -av /home/critic/clawd/Railway-1.21.1/ yogsothoth:/home/critic/minecraft/modding/Railway-1.21.1/ --exclude build --exclude .gradle
```

Or for single files:
```bash
scp /home/critic/clawd/Railway-1.21.1/path/to/file yogsothoth:/home/critic/minecraft/modding/Railway-1.21.1/path/to/file
```

## Common Issues

### Duplicate resource errors
The build.gradle.kts has `duplicatesStrategy = DuplicatesStrategy.EXCLUDE` to handle files that exist in both `src/resources` and `src/generated/resources`. The hand-crafted ones in `src/resources` take priority.

### Java version
Requires Java 21. yogsothoth has it at `/usr/lib/jvm/java-21-openjdk-amd64`.
