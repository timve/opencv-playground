brew install opencv3 --with-contrib --with-cuda --with-ffmpeg --with-tbb --with-java



/usr/local/Cellar/opencv3/3.1.0_3/share/OpenCV/java
bash-3.2$ ls -lh
total 2272
-rwxr-xr-x  1 administrator  admin   816K Jun  7 10:47 libopencv_java310.so
-rw-r--r--  1 administrator  admin   315K Jun  7 10:46 opencv-310.jar

ln -s /usr/local/Cellar/opencv3/3.1.0_3/share/OpenCV/java/libopencv_java310.so <projectdir>/src/main/resources/libopencv_java310.dylib



-Djava.library.path=./src/main/resources/lib/osx2/