knasta2Proj6: MyList.o Island.o knasta2Proj6.o
	g++ -o knasta2Proj6 MyList.o Island.o knasta2Proj6.o

knasta2Proj6.o: knasta2Proj6.cpp MyList.h Island.h
	g++ -c knasta2Proj6.cpp

Island.o: Island.cpp Island.h MyList.h
	g++ -c Island.cpp

MyList.o: MyList.cpp MyList.h
	g++ -c MyList.cpp

clean:
	rm MyList.o Island.o knasta2Proj6.o knasta2Proj6

run:
	./knasta2Proj6