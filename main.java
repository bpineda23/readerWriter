import java.util.concurrent.Semaphore;

class readerWriter {

    static Semaphore qASem = new Semaphore(1);
    static Semaphore qBSem = new Semaphore(1);
    static int whichQ;
    static int[] qA = new int[10];
    static int[] qB = new int[10];

/*    static class Halt implements Runnable {
        @Override
        public void run() {
            if (whichQ == 10) {
                System.exit(0);
            }
        }
    }*/

        static class Read implements Runnable {
            @Override
            public void run() {
                whichQ = 0;
                //while (true) {
                    try {
                        //Acquire Section
                        if (whichQ % 2 == 0) {
                            qASem.acquire();

                            //qBSem.release();

                            //Reading section
                            System.out.println("Buffer A " + qA);

                            //Releasing section
                            qASem.release();
                        }
                        else{
                                qBSem.acquire();

                                //qASem.release();

                                System.out.println("Buffer B " + qB);

                                //qBSem.release();

                            }
                            qASem.release();

                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    whichQ++;
                if (whichQ == 10) {
                    System.exit(0);}
            }
    }


            static class Write implements Runnable {
                @Override
                public void run() {
                    whichQ = 0;
                   // while (true) {
                        try {
                            if (whichQ % 2 == 0) {
                                qASem.acquire();

                                int i;
                                for (i = 0; i < 10; i++) {
                                    qB[i]++;
                                }

                                qASem.release(i = 10);
                            }
                            else {
                                qBSem.acquire();

                                int i;
                                for (i = 0; i <10; i++) {
                                    qA[i]++;
                                }

                                qASem.release();
                            }
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                        whichQ++;
                    //}
                }
            }

            public static void main(String[] args) {

                Read read = new Read();
                Write write = new Write();
                Thread t1 = new Thread(write);
                t1.setName("thread1");
                Thread t2 = new Thread(read);
                t2.setName("thread2");
                t1.start();
                t2.start();
            }

    }