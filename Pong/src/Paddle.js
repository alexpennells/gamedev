var Paddle = function(x, y, upKey, downKey) {
	// Call the constructor of the parent class from the scope of this object.
	GameObject.call(this, x, y);

	// Save the input keys to internal class variables.
	this.upKey = upKey;
	this.downKey = downKey;
	this.moveSpeed = 4;

	// The dimensions of the paddle for drawing and collision-checking.
	this.width = 20;
	this.height = 80;
}

// Inherits the prototype from GameObject.
Paddle.prototype = new GameObject();
Paddle.prototype.constructor = Paddle;

// By giving a definition to this function, we have overridden the draw
// function in the GameObject class.
/*Paddle.prototype.draw = function (context) {
}*/

// Handles the input from the user to move the paddle.
Paddle.prototype.update = function () {
	//Move the object based on the player input.
	if (InputManager.isPressed(this.upKey)) {
		this.y -= this.moveSpeed;
	}
	if (InputManager.isPressed(this.downKey)) {
		this.y += this.moveSpeed;
	}

	// Prevent the paddle from leaving the preset boundaries of the canvas.
	if (this.y < GameManager.borderSize) {
		this.y = GameManager.borderSize;
	}
	else if ((this.y + this.height) > (GameManager.canvas.height - GameManager.borderSize)) {
		this.y = GameManager.canvas.height - GameManager.borderSize - this.height;
	}
}
