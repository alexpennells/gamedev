#!/usr/bin/python

# This file is meant to be run from the root of the engine.
# ex. python scripts/genSpriteInfo.py

# This file will load the spriteInfo.txt file in res/Sprites and update the
# eSprites enumeration and SpriteInfo class with the new values.

import re

enum = 0 # The currently used enum value.
numValues = 5 # The number of values stored per sprite in spriteInfo.txt

# Write the headers for both files.
eSprites = open("js/eSprites.js", "w")
spriteInfo = open("js/SpriteInfo.js", "w")
eSprites.write("// This file was automatically generated using genSpriteInfo.py\n\nvar eSprites = {\n")
spriteInfo.write("// This file was automatically generated using genSpriteInfo.py\n\nvar SpriteInfo = [\n")
eSprites.close()
spriteInfo.close()

f = open("res/spriteInfo.txt")
content = f.readlines()

eSprites = open("js/eSprites.js", "a")
spriteInfo = open("js/SpriteInfo.js", "a")

for line in content:
    # Ignore the commented lines
    if line[0] == '#':
        continue

    text = re.split(', ', line.replace("\n", ""))

    # Write the contents of eSprites.js
    eSprites.write("    " + text[0] + ": " + str(enum) + ",\n")
    enum += 1

    # Write the contents of SpriteInfo.js
    spriteInfo.write("    [eSprites.");
    for i in range(0, numValues):
        if i != 1:
            spriteInfo.write(text[i] + ", ")
        else:
            spriteInfo.write("\'res/Sprites/" + text[i] + "\', ")

    spriteInfo.write("],\n")


eSprites.write("    SPRITE_COUNT: " + str(enum) +",\n}")
eSprites.close()
spriteInfo.write("];")
spriteInfo.close()
f.close()
