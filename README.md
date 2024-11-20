First, change the remote debug mode to true to activate the debug mode.

# configuration.properties
browser=chrome
remoteDebug=false


Then comment out the lines shown below in the BaseTest class.

 @AfterEach
    public void tearDown() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            //driver.quit();
           // threadLocalDriver.remove(); // Bellek sızıntısını önlemek için
        }
    }

    
