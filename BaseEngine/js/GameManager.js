// Handles the main loop of the game, the drawing, and holds the references to the game objects.
var GameManager = {
    // requestAnimationFrame runs at 60fps by default.
    fps: 1000.0 / 60.0,

    // An array of all of the GameObjects in the current room.
    objects: [],

    // Initializes the game.
    init: function () {
        InputManager.init();
        SpriteManager.init();
        SpriteManager.loadSprites([eSprites.PLAYER_LEFT,eSprites.PLAYER_UP,eSprites.PLAYER_DOWN, eSprites.PLAYER_RIGHT]);
    },

    // Will be called by the SpriteManager once all of the images have been loaded.
    // Draws the room and then begins the main game loop.
    startRoom: function () {
        this.drawBackground();
        this.createPlayer();
        window.requestAnimationFrame(this.gameLoop);
    },

    // Creates the player object.
    createPlayer: function () {
        this.objects.push(new Player(0, 0));
    },

    // Redraws the background of the room.
    drawBackground: function () {
        var canvas = document.getElementById('canvas');
        var canvasCxt = canvas.getContext('2d');
        canvasCxt.clearRect(0, 0, canvas.width, canvas.height);
        canvasCxt.fillStyle = 'green';
        canvasCxt.fillRect(0, 0, canvas.width, canvas.height);
    },

    // Performs each object's step event in the room.
    moveObjects: function () {
        for (var i = 0; i < this.objects.length; ++i)
            this.objects[i].step();
    },

    // Performs each object's draw event in the room.
    drawObjects: function () {
        for (var i = 0; i < this.objects.length; ++i)
            this.objects[i].draw();
    },

    redrawScreen: function () {
        this.drawObjects();
    },

    gameLoop: function (now) {
        window.requestAnimationFrame(GameManager.gameLoop);
        GameManager.moveObjects();
        GameManager.redrawScreen();
    },
}
