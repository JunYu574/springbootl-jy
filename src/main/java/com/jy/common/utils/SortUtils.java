package com.jy.common.utils;

/**
 * @Author: JunYu
 * @Date: 2024/4/24 21:27
 * @Description:
 * @Version: V1.0.0
 */
public class SortUtils {

    /**
     * 数组两个数据交换位置
     */
    private static void swapValue(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

//    ============================== 冒泡排序 ==============================
    /**
     * 1.冒泡排序
     * @param arr 待排序数组
     */
    public static void bubbleSort(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        boolean flag;//当一轮下来未发生交换，排序完成，跳出循环
        for (int i = 0; i < arr.length - 1; i++) {
            flag = true;//重置标志位
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if(arr[j] > arr[j+1]){
                    swapValue(arr,j,j+1);
                    flag = false;//标记发生交换
                }
            }
//            System.out.println("第" + (i+1) + "轮:"+ Arrays.toString(arr));
            if(flag){//无交换，排序结束
                break;
            }
        }
    }

//    ============================== 选择排序 ==============================
    /**
     * 2.选择排序
     * @param arr 待排序数组
     */
    public static void selectionSort(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        int min;
        for (int index = 0; index < arr.length - 1; index++) {
            min = index;
            for (int i = index + 1; i < arr.length; i++) {
                if(arr[i] < arr[min]){
                    min = i;
                }
            }
            if(index != min) {
                swapValue(arr, index, min);
            }
//            System.out.println("第" + (index+1) + "轮:"+ Arrays.toString(arr));
        }
    }

    /**
     * 选择排序，一次循环选出最大值与最小值
     */
    public static void selectionSort2(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        int left = 0;
        int right = arr.length - 1;
        while (left < right){
            int min = left;
            int max = right;
            for (int i = left; i <= right; i++) {
                if(arr[i] < arr[min]){
                    min = i;
                }
                if(arr[i] > arr[max]){
                    max = i;
                }
            }
            swapValue(arr, max, right);
            if(min == right){
                min = max;
            }
            swapValue(arr, min, left);
            left++;
            right--;
//            System.out.println("第" + left + "轮:"+ Arrays.toString(arr));
        }
    }

//    ============================== 直接插入排序 ==============================
    /**
     * 3.直接插入排序
     * @param arr 待排序数组
     */
    public static void insertionSort(int[] arr) {
//        System.out.println("初始:"+ Arrays.toString(arr));
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if(arr[j] < arr[j-1]){
                    swapValue(arr,j,j-1);
                }else {
                    break;
                }
            }
            /*int j = i;
            int temp = arr[i];
            while (j > 0) {
                if(temp < arr[j-1]){
                    arr[j] = arr[--j];
                }else {
                    break;
                }
            }
            arr[j] = temp;*/
//            System.out.println("第" + i + "轮:"+ Arrays.toString(arr));
        }
    }

//    ============================== 希尔排序 ==============================
    /**
     * 4.希尔排序
     * @param arr 待排序数组
     */
    public static void shellSort(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        int knuth = 1;
        while (knuth <= arr.length/3){//克努特序列
            knuth = knuth * 3 + 1;
        }
        for (int h = knuth; h > 0; h = (h - 1)/3) {//增量
            for (int i = h; i < arr.length; i++) {
                for (int j = i; j > h - 1; j -= h) {
                    if(arr[j] < arr[j-h]){
                        swapValue(arr,j,j-h);
                    }else {
                        break;
                    }
                }
                /*int j = i;
                int temp = arr[j];
                while (j > h - 1) {
                    if(temp < arr[j-h]){
                        arr[j] = arr[j-h];
                        j -= h;
                    }else {
                        break;
                    }
                }
                arr[j] = temp;*/
            }
//            System.out.println("第" + h + "轮:"+ Arrays.toString(arr));
        }
    }

//    ============================== 快速排序 ==============================
    /**
     * 5.快速排序
     * @param arr 待排序数组
     */
    public static void quickSort(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序分组
     */
    public static void quickSort(int[] arr, int start, int end){
        if(start < end){
            int index = getIndex(arr,start,end);
//            System.out.println(index + ":"+ Arrays.toString(arr));
            quickSort(arr, start, index - 1);
            quickSort(arr, index + 1, end);
        }
    }

    /**
     * 快速排序获取基准下标
     */
    private static int getIndex(int[] arr, int start, int end) {
        int i = start;
        int j = end;
        int x = arr[i];
        while (i < j){
            //由后向前找比他小的数，找到后挖出此数填到前一个坑中
            while (i < j && arr[j] >= x){
                j--;
            }
            if(i < j){
                arr[i++] = arr[j];
            }
            //由前向后找比他大的数，找到后挖出此数填到前一个坑中
            while (i < j && arr[i] < x){
                i++;
            }
            if(i < j){
                arr[j--] = arr[i];
            }
        }
        arr[i] = x;
        return i;
    }

    public static void quickSort2(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        quickSort2(arr, 0, arr.length - 1);
    }

    public static void quickSort2(int[] arr, int start, int end){
        if(start < end){
            int index = getIndex2(arr,start,end);
//            System.out.println(index + ":"+ Arrays.toString(arr));
            quickSort2(arr, start, index - 1);
            quickSort2(arr, index + 1, end);
        }
    }

    private static int getIndex2(int[] arr, int start, int end) {
        int i = start + 1;
        int j = end;
        int temp = arr[start];
        while (i < j){
            //从前往后，找到比基准数大的数
            while (i < j && arr[i] < temp){
                i++;
            }
            //从后往前，找到比基准数小的数
            while (i < j && arr[j] > temp){
                j--;
            }
            //大数在小数前，交换位置
            if(i < j){
                swapValue(arr,i++,j--);
            }
        }
        //若i=j时，j处的数据仍大于基准数，则将前一位数与基准数交换
        if(temp < arr[j]){
            j--;
        }
        //最后待交换数在基准数后边，交换位置
        if(start < j){
            swapValue(arr,start,j);
        }
        return j;
    }

//    ============================== 归并排序 ==============================
    /**
     * 6.归并排序
     * @param arr 待排序数组
     */
    public static void mergeSort(int[] arr) {
//        System.out.println("初始:"+ Arrays.toString(arr));
        mergeSort(arr,0,arr.length-1);
    }

    /**
     * 对数组拆分
     */
    public static void mergeSort(int[] arr, int startIndex, int endIndex) {
        if(startIndex<endIndex){
            //计算中间索引
            int centerIndex = (startIndex+endIndex)/2;
            mergeSort(arr,startIndex,centerIndex);
            mergeSort(arr,centerIndex+1,endIndex);
//            System.out.println(centerIndex + ":"+ Arrays.toString(arr));
            merge(arr,startIndex,centerIndex,endIndex);
//            System.out.println(centerIndex + ":"+ Arrays.toString(arr));
        }
    }

    /**
     * 对拆分后的数组进行合并排序
     */
    public static void merge(int[] arr, int startIndex, int centerIndex, int endIndex){
        //定义一个临时数组
        int[] tempArr = new int[endIndex-startIndex+1];
        //定义左边数组的起始索引
        int i = startIndex;
        //定义右边数组的起始索引
        int j = centerIndex + 1;
        //定义临时数组的起始索引
        int index = 0;
        //比较两个数组的元素大小，往临时数组里放
        while (i <= centerIndex && j <= endIndex){
            if(arr[i] <= arr[j]){
                tempArr[index++] = arr[i++];
            }else {
                tempArr[index++] = arr[j++];
            }
        }
        //处理剩余元素
        while (i <= centerIndex){
            tempArr[index++] = arr[i++];
        }
        while (j <= endIndex){
            tempArr[index++] = arr[j++];
        }
        System.arraycopy(tempArr, 0, arr, startIndex, tempArr.length);
    }

//    ============================== 基数排序 ==============================
    /**
     * 7.基数排序，桶排序
     * @param arr 待排序数组
     */
    public static void radixSort(int[] arr){
//        System.out.println("初始:"+ Arrays.toString(arr));
        //定义一个二位数组，放10个桶
        int[][] tempArr = new int[10][arr.length];
        //记录桶中数据个数
        int[] counts = new int[10];
        //循环次数
        int max = getMax(arr);
        int len = String.valueOf(max).length();
        for (int i = 0, n = 1; i < len; i++, n *= 10) {
            for (int value : arr) {
                //获取每个位置上的数字
                int ys = value / n % 10;
                tempArr[ys][counts[ys]++] = value;
            }
            //取出桶中的元素
            int index = 0;
            for (int k = 0; k < counts.length; k++) {
                if (counts[k] > 0){
                    for (int h = 0; h < counts[k]; h++) {
                        arr[index++] = tempArr[k][h];
                    }
                    counts[k] = 0;
                }
            }
//            System.out.println("第" + (i+1) + "轮:"+ Arrays.toString(arr));
        }

    }

    /**
     * 获取数组中最大的数据，用其长度作为基数排序的循环次数
     */
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

//    ============================== 堆排序 ==============================
    /**
     * 8.堆排序
     * @param arr 待排序数组
     */
    public static void maxHeapSort(int[] arr) {
//        System.out.println("初始:"+ Arrays.toString(arr));
        //调整成大顶堆的方法
        //定义开始调整的位置
        int startIndex = arr.length/2 - 1;
        for (int i = startIndex; i >= 0; i--) {
            maxHeapSort(arr,arr.length,i);
        }
//        System.out.println("第" + 1 + "轮:"+ Arrays.toString(arr));
        //经过上面的操作后，已经把数组变成一个大顶堆，把根元素和最后一个元素进行调换
        for (int i = arr.length-1; i > 0; i--) {
            swapValue(arr,0,i);
            //换完之后，再把剩余元素调成大顶堆
            maxHeapSort(arr,i,0);
//            System.out.println("第" + (arr.length-i) + "轮:"+ Arrays.toString(arr));
        }
    }

    /**
     * @param size 数组大小
     * @param index 当前堆顶
     */
    public static void maxHeapSort(int[] arr, int size, int index){
        //获取左右字节的索引
        int leftNodeIndex = index*2+1;
        int rightNodeIndex = index*2+2;
        //查找最大节点所对应的索引
        int maxIndex = index;
        if(leftNodeIndex<size&&arr[leftNodeIndex]>arr[maxIndex]){
            maxIndex = leftNodeIndex;
        }
        if(rightNodeIndex<size&&arr[rightNodeIndex]>arr[maxIndex]){
            maxIndex = rightNodeIndex;
        }
        //换位
        if(maxIndex != index){
            swapValue(arr,maxIndex,index);
            //调换完之后，可能会影响到子树，不是大顶堆，继续调换
            maxHeapSort(arr,size,maxIndex);
        }

    }
}
