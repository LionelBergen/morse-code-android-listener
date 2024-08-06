Bluetooth Morse Code Reader  
---------------------------  
Reads a bluetooth morse signals (https://github.com/LionelBergen/morse-code-project)   


Development Notes  
-----------------   
Using wifi debugging on a physical device with developer options enabled.  
I have to use commnad line adb commands, as the IDE doesn't seem to be working to pair.   
**Step 1:** `adb pair 10.0.0.9:41749`    
`Enter pairing code: 366629`   
`Successfully paired to 10.0.0.9:41749`  
**Step 2:** (Note the port changes after pairing)  
`adb connect 10.0.0.9:34975`   
`connected to 10.0.0.9:34975`   