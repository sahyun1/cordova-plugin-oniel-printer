var exec = require('cordova/exec');
var OnielPrinter = {
    platforms: ['android'],

    isSupported: function () {
        if (window.device) {
            var platform = window.device.platform;
            if ((platform !== undefined) && (platform !== null)) {
                return (this.platforms.indexOf(platform.toLowerCase()) >= 0);
            }
        }
        return false;
    },
    printText: function (text, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printText', [text]);
    },
    printBarcode: function (type, data, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printBarcode', [type, data]);
    },
    printImage: function (image, width, height, align, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printImage', [image, width, height, align]);
    },
};
module.exports = OnielPrinter;