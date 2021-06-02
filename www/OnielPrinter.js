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

    setLandscape: function(isLandscape, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'setLandscape', [isLandscape]);
    },
    printText: function (text, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printText', [text]);
    },
    printTextObj: function (obj, printerAddress, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printTextObj', [obj, printerAddress]);
    },
    getPairedDevices: function (onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'getPairedDevices');
    },
    printBarcode: function (type, data, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printBarcode', [type, data]);
    },
    printImage: function (image, width, height, align, onSuccess, onError) {
        exec(onSuccess, onError, 'OnielPrinter', 'printImage', [image, width, height, align]);
    },
};
module.exports = OnielPrinter;
