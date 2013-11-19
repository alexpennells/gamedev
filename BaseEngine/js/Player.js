// The class representing the playable character.
function Player (x, y) {
    GameObject.call(this, x, y, eSprites.PLAYER_DOWN);
    this.imageSpeed = 0;
}

// Inherits from GameObject.
Player.prototype = new GameObject();
Player.prototype.constructor = Player;

// OVERRIDE
Player.prototype.step = function() {
    this.updateSprite();
    this.move();
};

// Moves the player based on the user input.
Player.prototype.move = function() {
    // Process the arrow key inputs.
    if (InputManager.isPressed(eKeys.W))
        this.y -= 4;
    else if (InputManager.isPressed(eKeys.S))
        this.y += 4;

    if (InputManager.isPressed(eKeys.A))
        this.x -= 4;
    else if (InputManager.isPressed(eKeys.D))
        this.x += 4;

    // Prevent the character from going off-screen.
    if (this.x > $('#canvas').width() - 64)
        this.x = $('#canvas').width() - 64;
    if (this.x < 0)
        this.x = 0;
    if (this.y > $('#canvas').height() - 64)
        this.y = $('#canvas').height() - 64;
    if (this.y < 0)
        this.y = 0;
};

// Changes the sprite or animation speed based on the input of the user.
Player.prototype.updateSprite = function () {
    if (InputManager.isPressed(eKeys.A))
        this.setSprite(eSprites.PLAYER_LEFT);
    else if (InputManager.isPressed(eKeys.D))
        this.setSprite(eSprites.PLAYER_RIGHT);

    if (InputManager.isPressed(eKeys.W))
        this.setSprite(eSprites.PLAYER_UP);
    else if (InputManager.isPressed(eKeys.S))
        this.setSprite(eSprites.PLAYER_DOWN);

    // Only animate if the user is moving.
    if (InputManager.isPressed(eKeys.A) || InputManager.isPressed(eKeys.D)
        || InputManager.isPressed(eKeys.S) || InputManager.isPressed(eKeys.W))
        this.imageSpeed = 0.15;
    else {
        this.imageSpeed = 0;
        this.imageIndex = 0;
    }
};