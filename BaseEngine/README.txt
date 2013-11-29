~ BaseEngine Readme ~

A simple JavaScript/HTML game engine using the canvas for drawing and an inheritance style model for class creation. I originally was planning on using this for a tutorial on JS/HTML I was running, but decided not to use it as it got too extensive and became to large to teach in a learning session. Can be used in whichever way you want, so feel free to steal.

Still in development, I'll basically work on it whenever I get the time.

Most graphical functionality is already available. New graphics can be auto-added to the engine through use of the genSpriteInfo.py script. Sprite pre-loading is handled by the SpriteManager and the engine currently supports sprite animations. For better drawing efficiency, each object is given its own canvas to draw to and is only re-drawn if there is a change in sprite or animation frame.
