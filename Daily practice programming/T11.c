#include <stdio.h>

void reverseArray(int arr[], int n) {
  // Loop through up to half of the array, including the middle element
  for (int i = 0; i < n / 2; i++) {
    // Swap elements from beginning and end
    int temp = arr[i];
    arr[i] = arr[n - i - 1];
    arr[n - i - 1] = temp;
  }
}

int main() {
  int n;

  //printf("Enter the size of the array: ");
  scanf("%d", &n);

  int arr[n];

  //printf("Enter the elements of the array: ");
  for (int i = 0; i < n; i++) {
    scanf("%d", &arr[i]);
  }

  reverseArray(arr, n);

  //printf("Reversed array is ");
  for (int i = 0; i < n; i++) {
    printf("%d ", arr[i]);
  }

  printf("\n");

  return 0;
}
