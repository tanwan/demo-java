cd ../../../
if [ ! -d "build" ]; then
  mkdir build
fi
cd build

url=https://github.com/protocolbuffers/protobuf/releases/download/v21.2/protoc-21.2-osx-x86_64.zip

if [ ! -f "protoc/bin/protoc" ]; then
  wget $url -O temp.zip
  unzip -d protoc temp.zip
  rm -f temp.zip
fi

# lite
if [ ! -d "lite" ]; then
  mkdir lite
fi
protoc/bin/protoc -I=../src/main/proto/ --java_out=lite:./lite ../src/main/proto/*.proto

# full
if [ ! -d "full" ]; then
  mkdir full
fi
protoc/bin/protoc -I=../src/main/proto/ --java_out=./full ../src/main/proto/*.proto

