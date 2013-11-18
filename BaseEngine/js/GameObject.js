// A basic game object.

function GameObject (x, y, sprite) {
    // Integers specifying the coordinates of the object.
    this.x = x;
    this.y = y;

    // A Sprite object containing info about this object's sprite.
    this.sprite = SpriteManager.getSprite(sprite);

    // A float which represents the current frame of the sprite animation.
    this.imageIndex = 0;

    // An integer specifying the exact frame being drawn for the object.
    this.curImageIndex = -1;

    // Specifies the amount to increment imageIndex at every draw step.
    this.imageSpeed = 1;

    // The canvas and context used for drawing this object.
    if (this.sprite != null) {
        this.canvas = SpriteManager.createCanvas(this.sprite.width, this.sprite.height);
        this.context = this.canvas.getContext('2d');
        this.draw();
    }
}

// Updates the animation frame based on the image speed.
GameObject.prototype.animate = function() {
    this.imageIndex += this.imageSpeed;
    if (this.imageIndex >= this.sprite.numFrames)
        this.imageIndex -= this.sprite.numFrames;
};

// Returns true if the image needs to be redrawn due to the animation frame changing.
GameObject.prototype.needsRedraw = function() {
    var newImageIndex = Math.floor(this.imageIndex);
    if (newImageIndex != this.curImageIndex) {
        this.curImageIndex = newImageIndex;
        return true;
    }
    return false;
};

// Given a value from eSprites, changes the sprite for the object.
GameObject.prototype.setSprite = function(sprite) {
    this.sprite = SpriteManager.getSprite(sprite);

    // Ensure that the sprite will be redrawn on the next screen redraw.
    this.curImageIndex = -1;
};


// Moves the canvas and updates the animation for the object. Only redraws
// the object if the current animation frame has changed.
GameObject.prototype.draw = function() {
    this.animate();

    // Move the canvas to the new coordinates.
    this.canvas.style.left = this.x + 'px';
    this.canvas.style.top = this.y + 'px';

    if (this.needsRedraw()) {
        this.context.clearRect(0, 0, this.sprite.width, this.sprite.height);
        this.context.drawImage(this.sprite.image,
                               this.curImageIndex * this.sprite.width, 0,
                               this.sprite.width, this.sprite.height, 0, 0,
                               this.sprite.width, this.sprite.height);
    }
};

// An empty stub that should be overidden by inheriting classes. Is called prior to each screen redraw. 
GameObject.prototype.step = function() {
};
