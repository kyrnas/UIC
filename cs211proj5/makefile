proj5: MyStack.o knasta2Proj5.o
	g++ -o proj5 MyStack.o knasta2Proj5.o

knasta2Proj5.o: knasta2Proj5.cpp MyStack.h
	g++ -c knasta2Proj5.cpp

MyStack.o: MyStack.cpp MyStack.h
	g++ -c MyStack.cpp

clean:
	rm MyStack.o knasta2Proj5.o proj5