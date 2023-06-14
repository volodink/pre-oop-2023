#include <iostream>
#include <random>

using namespace std;

// Node class definition
class Node {
public:
    int data;
    Node* next;

    Node(int value) {
        data = value;
        next = NULL;
    }
};

// Merge two sorted linked lists into one sorted linked list
Node* Merge(Node* list1, Node* list2) {
    if (list1 == NULL) {
        return list2;
    }
    if (list2 == NULL) {
        return list1;
    }
    
    if (list1->data <= list2->data) {
        list1->next = Merge(list1->next, list2);
        return list1;
    }
    else {
        list2->next = Merge(list1, list2->next);
        return list2;
    }
}

// Get the middle node of the linked list
Node* GetMiddle(Node* head) {
    if (head == NULL) {
        return NULL;
    }

    Node* slow = head;
    Node* fast = head->next;

    while (fast != NULL) {
        fast = fast->next;
        if (fast != NULL) {
            slow = slow->next;
            fast = fast->next;
        }
    }

    return slow;
}

// Merge sort function to sort the linked list
Node* MergeSort(Node* head) {
    if (head == NULL || head->next == NULL) {
        return head;
    }

    Node* middle = GetMiddle(head);
    Node* right = MergeSort(middle->next);
    middle->next = NULL;
    Node* left = MergeSort(head);

    return Merge(left, right);
}

// Display the linked list
void DisplayLinkedList(Node* head) {
    Node* current = head;
    while (current != NULL) {
        cout << current->data << " ";
        current = current->next;
    }
}

int main(int argc, char **argv) {
    std::random_device rd; // obtain a random number from hardware
    std::mt19937 gen(rd()); // seed the generator
    std::uniform_int_distribution<> distr(-10, 10); // define the range

    cout << "Random data test" << endl;
    cout << "----------------" << endl;

    // Create a linked list
    Node* head = new Node(distr(gen));
    head->next = new Node(distr(gen));
    head->next->next = new Node(distr(gen));
    head->next->next->next = new Node(distr(gen));
    head->next->next->next->next = new Node(distr(gen));

    cout << "Before sorting: ";
    DisplayLinkedList(head);

    // Sort the linked list
    head = MergeSort(head);

    cout << "\nAfter sorting: ";
    DisplayLinkedList(head);

    cout << endl;
    cout << "----------------" << endl;


    cout << "Ordered data test" << endl;
    cout << "----------------" << endl;

    // Create a linked list
    head = new Node(1);
    head->next = new Node(2);
    head->next->next = new Node(3);
    head->next->next->next = new Node(4);
    head->next->next->next->next = new Node(5);

    cout << "Before sorting: ";
    DisplayLinkedList(head);

    // Sort the linked list
    head = MergeSort(head);

    cout << "\nAfter sorting: ";
    DisplayLinkedList(head);

    cout << endl;
    cout << "----------------" << endl;


    cout << "Reverse order data test" << endl;
    cout << "----------------" << endl;

    // Create a linked list
    head = new Node(5);
    head->next = new Node(4);
    head->next->next = new Node(3);
    head->next->next->next = new Node(2);
    head->next->next->next->next = new Node(1);

    cout << "Before sorting: ";
    DisplayLinkedList(head);

    // Sort the linked list
    head = MergeSort(head);

    cout << "\nAfter sorting: ";
    DisplayLinkedList(head);

    cout << endl;
    cout << "----------------" << endl;


    return 0;
}
