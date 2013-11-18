function Sprite (id, image, width, height, numFrames) {
    // A value from the eSprites enumeration specifying the image to draw.
    this.id = id;

    // An Image object containing the image to draw.
    this.image = image;

    // An integer specifying the width/height of a single frame.
    this.width = width;
    this.height = height;

    // An integer specifying the number of frames for the animation.
    this.numFrames = numFrames;
}
