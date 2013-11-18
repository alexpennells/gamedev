var InputManager = {
    // Contains either true or false for every key code specifying whether it is currently pressed.
    pressedKeys: [],

    init: function () {
        // Initialize the pressedKeys array to be false for every key.
        for (var i = 0; i < 91; i++)
            this.pressedKeys.push(false);

        this.bindKeys();
    },

    // Binds the key press and key up events to update the pressedKeys array.
    bindKeys: function () {
        $(document).keydown(function (e) {
            switch (e.which) {
                case eKeys.UP:
                    InputManager.pressedKeys[eKeys.UP] = true;
                    break;
                case eKeys.DOWN:
                    InputManager.pressedKeys[eKeys.DOWN] = true;
                    break;
                case eKeys.LEFT:
                    InputManager.pressedKeys[eKeys.LEFT] = true;
                    break;
                case eKeys.RIGHT:
                    InputManager.pressedKeys[eKeys.RIGHT] = true;
                    break;
            }
        });

        $(document).keyup(function (e) {
            switch (e.which) {
                case eKeys.UP:
                    InputManager.pressedKeys[eKeys.UP] = false;
                    break;
                case eKeys.DOWN:
                    InputManager.pressedKeys[eKeys.DOWN] = false;
                    break;
                case eKeys.LEFT:
                    InputManager.pressedKeys[eKeys.LEFT] = false;
                    break;
                case eKeys.RIGHT:
                    InputManager.pressedKeys[eKeys.RIGHT] = false;
                    break;
            }
        });
    },

    // Given a value from eKeys, returns true if the key is pressed, false otherwise.
    isPressed: function (key) {
        return this.pressedKeys[key];
    },
};
