var SpriteManager = {
    // A list of Sprite objects already preloaded.
    loaded: [],

    // A mapping of eSprite values to their current index in the loaded array. Will be -1 if the sprite is not loaded.
    index: [],

    // Since images are loaded asynchronously, this integer specifies the number of images still loading after a call 
    // to loadSprites. Once this value reaches 0 again, GameManager.startRoom() will be called.
    imagesLoading: 0,

    // Should be called to initialize the SpriteManager.
    init: function () {
        for (var i = 0; i < eSprites.SPRITE_COUNT; ++i)
            this.index.push(-1);
    },

    clearIndex: function () {
        for (var i = 0; i < eSprites.SPRITE_COUNT; ++i)
            this.index[i] = -1;
    },

    // Attempts to load the Sprite object for the given eSprite value and return
    // it. If it does not exist already, returns null.
    getSprite: function (id) {
        if (this.index[id] == -1) {
            console.log('Unable to load sprite.');
            return null;
        }
        else
            return this.loaded[this.index[id]];
    },

    // Given a list of eSprite values, loads any new sprites and adds them to the
    // loaded and index lists.
    loadSprites: function (sprites) {
        this.imagesLoading = 0;

        for (var i = 0; i < sprites.length; ++i) {
            if (!this.isSpriteLoaded(sprites[i])) {
                // Create the Sprite object and insert it into the loaded array.
                var image = new Image();
                image.src = SpriteInfo[sprites[i]][eSpriteInfo.FILE];

                // When the image has been loaded, subtract 1 from the total number of loading images.
                image.onload = function() { SpriteManager.imagesLoading--; };

                var newSprite = new Sprite(sprites[i], image,
                                           SpriteInfo[sprites[i]][eSpriteInfo.FRAME_WIDTH],
                                           SpriteInfo[sprites[i]][eSpriteInfo.FRAME_HEIGHT],
                                           SpriteInfo[sprites[i]][eSpriteInfo.NUM_FRAMES]);
                this.loaded.push(newSprite);

                // Update the index of the sprite.
                this.index[sprites[i]] = this.loaded.length - 1;
                this.imagesLoading++;
            }
        }

        setTimeout(this.checkAllSpritesLoaded, GameManager.fps);
    },

    // Continuously calls itself until all of the images have been loaded.
    // Note: This function is not called within the scope of SpriteManager.
    checkAllSpritesLoaded: function() {
        if (SpriteManager.imagesLoading != 0)
            setTimeout(SpriteManager.checkAllSpritesLoaded, GameManager.fps);
        else
            GameManager.startRoom();
    },

    // Given a eSprite value, checks if the sprite exists in the index.
    isSpriteLoaded: function (sprite) {
        if (this.index[sprite] == -1)
            return false;
        else
            return true;
    },

    // Clears all of the preloaded sprites.
    clearSprites: function () {
        this.loaded = [];
        this.clearIndex();
    },

    // Creates a new canvas in the body and returns its reference.
    createCanvas: function (w, h) {
        var newCanvas = document.createElement('canvas');
        newCanvas.width = w;
        newCanvas.height = h;
        newCanvas.style.position = 'absolute';
        document.body.appendChild(newCanvas);
        return newCanvas;
    },
};
