# Série de comandos - se funcionar, melhor


sudo apt-get install build-essential cmake unzip pkg-config


sudo apt-get install libjpeg-dev libpng-dev libtiff-dev




sudo apt-get install libavcodec-dev libavformat-dev libswscale-dev libv4l-dev

sudo apt-get install libxvidcore-dev libx264-dev


sudo apt-get install libgtk-3-dev

sudo apt-get install libatlas-base-dev gfortran


sudo apt-get install python3-dev


mkdir ~/tmp
cd ~/tmp


wget -O opencv.zip https://github.com/opencv/opencv/archive/3.4.4.zip
wget -O opencv_contrib.zip https://github.com/opencv/opencv_contrib/archive/3.4.4.zip


unzip opencv.zip

unzip opencv_contrib.zip


mv opencv-3.4.4/ opencv
mv opencv_contrib-3.4.4/ opencv_contrib



cd ~/tmp/opencv
mkdir build
cd build
cmake -D CMAKE_BUILD_TYPE=RELEASE \
    -D CMAKE_INSTALL_PREFIX=/usr/local \
    -D INSTALL_PYTHON_EXAMPLES=ON \
    -D INSTALL_C_EXAMPLES=OFF \
    -D OPENCV_ENABLE_NONFREE=ON \
    -D OPENCV_EXTRA_MODULES_PATH=~/tmp/opencv_contrib/modules \
    -D PYTHON_EXECUTABLE=/usr/bin/python3 \
    -D BUILD_EXAMPLES=ON ..



make -j4


sudo make install


sudo ldconfig



sudo apt install python3-pip


sudo -H pip3 install jupyter






