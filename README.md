Datamax Oneil Cordova Plugin
==============
This is fork of https://github.com/megatdaharus/cordova-plugin-oniel-printer

I have added a method that takes Array to print multiple line in a one go.

I have tested against android only with 2te model.

### Supported Platforms

- Android 

## Installation
Below are the methods for installing this plugin automatically using command line tools.

### Using the Cordova CLI

```
$ cordova plugin add https://github.com/sahyun1/cordova-plugin-oniel-printer.git

```
### Using in Ionic

Declare cordova on top of the file just after other imports
```
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

declare let cordova: any;

@Component({
  selector: 'app-folder',
  templateUrl: './folder.page.html',
  styleUrls: ['./folder.page.scss'],
})

```

### Print multiple lines
```javascript
type Line = {
    label: string, // text to print
    row: number, // position X
    col: number // position Y,
    param?: string // e.g. bold, align-right
}


cordova.plugins.bixolonPrint.printTextObj(obj: Lines[], printerAddress, isLandscape, onSuccess, onError);
```
