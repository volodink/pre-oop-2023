#include <stdio.h>
#include <stdlib.h>

// Define the linked list node struct
typedef struct Node {
    int data;
    struct Node* next;
} Node;

// Print the linked list
void print_list(Node* head) {
    while (head != NULL) {
        printf("%d ", head->data);
        head = head->next;
    }
    printf("\n");
}

// Insert a node at the end of the linked list
void insert_last(Node** headRef, int newData) {
    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->data = newData;
    newNode->next = NULL;
    
    if (*headRef == NULL) {
        *headRef = newNode;
        return;
    }
    
    Node* lastNode = *headRef;
    while (lastNode->next != NULL) {
        lastNode = lastNode->next;
    }
    
    lastNode->next = newNode;
}

// Retrieve the node at the given index (starting at 0), or NULL if index is out of bounds
Node* get_by_index(Node* head, int index) {
    Node* current_node = head;
    int i = 0;
    while (current_node != NULL) {
        if (i == index) {
            return current_node;
        }
        current_node = current_node->next;
        i++;
    }
    return NULL;
}

// Delete an item from the list at a given index
void delete_by_index(Node** head, int index) {
    // If list is empty, nothing to delete
    if (*head == NULL)
        return;

    // Store head node
    Node* temp = *head;

    // If head needs to be removed
    if (index == 0) {
        // Change head to next node
        *head = temp->next;
        // Free old head memory
        free(temp);
        return;
    }

    // Find previous node of the node to be deleted
    for (int i = 0; temp != NULL && i < index - 1; i++)
        temp = temp->next;

    // If index is out of range, do nothing
    if (temp == NULL || temp->next == NULL)
        return;

    // Node temp->next is the node to be deleted
    // Store pointer to the next node
    Node* next = temp->next->next;

    // Free memory of the node to be deleted
    free(temp->next);

    // Unlink the deleted node from the list
    temp->next = next;
}

// Finds the node by its data value
Node* find_node_by_value(Node** head, int val) {
    Node* ptr = *head;
    while (ptr != NULL) {
        if (ptr->data == val) {
            return ptr;
        }
        ptr = ptr->next;
    }
    return NULL;
}

int main() {
    Node* head = NULL; // Initialize an empty linked list
    
    // Insert nodes at the end of the linked list
    insert_last(&head, 1);
    insert_last(&head, 2);
    insert_last(&head, 3);
    printf("Linked list after inserting at the end: ");
    print_list(head); // Expected output: 1 2 3

    // Get element by index
    int element_id = 2;
    Node* element = get_by_index(head, element_id);
    printf("Element data at index %d is %d.\n", element_id, element->data); 
    
    // Find node by value
    int element_idx = 1;
    Node* found_element = find_node_by_value(&head, element_idx);
    if (found_element == NULL) {
        printf("Element not found.\n");
    } else {
        printf("Element data %d at %lu.\n", found_element->data, (unsigned long int)found_element);
    }

    // Update element by index
    found_element = find_node_by_value(&head, element_idx);
    int new_value = 42;
    if (found_element == NULL) {
        printf("Element not found.\n");
    } else {
        found_element->data = new_value;
        printf("Linked list after updating element: ");
        print_list(head);
    }

    // Delete element by index
    element_id = 2;
    delete_by_index(&head, element_id);
    printf("Linked list after deleting element at index %d: ", element_id);
    print_list(head); // Expected output: 42 2
    
    return 0;
}