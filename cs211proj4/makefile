runnable: queue.o do.o proj4.o
	gcc -o runnable queue.o do.o proj4.o

proj4.o: proj4.c fastFoodRest.h
	gcc -c proj4.c

do.o: do.c fastFoodRest.h
	gcc -c do.c

queue.o: queue.c fastFoodRest.h
	gcc -c queue.c

clean:
	rm queue.o do.o proj4.o runnable