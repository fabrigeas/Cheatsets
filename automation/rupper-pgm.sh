cd pgm
cd ruppert.0
cd ../
cp ruppert0/.best .
bin/pgm |sort -k3 | tail -n5
mv {} .pat.learn
cd ruppert0
./pgm
#FFFFFF
