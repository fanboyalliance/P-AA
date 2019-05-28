#include <iostream>
#include <vector>
#include <sstream>
#include <string>
#include <cstring>

using namespace std;

class AK {
    static const int defaultLength = 64;
    struct Trie {
        int vertices[defaultLength];
        vector<int> patternCount;
        bool ifStr;
        int suffLink;
        int autoMove[defaultLength];
        int parent;
        char symbol;
    };
    vector<Trie> bohr;

    Trie makeTrie(int parent, char symb) {
        Trie vertex;
        vertex.ifStr = false;
        vertex.suffLink = -1;
        vertex.parent = parent;
        vertex.symbol = symb;
        initArrays(vertex.vertices);
        initArrays(vertex.autoMove);

        return vertex;
    }

    void initArrays(int *array) {
        for (int i = 0; i < defaultLength; i++) {
            array[i] = -1;
        }
    }

    void addStringToTrie(const string &str, vector<string> &pattern) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            char symb = str[i] - 'A';
            if (bohr[num].vertices[symb] == -1) {
                bohr.push_back(makeTrie(num, symb));
                bohr[num].vertices[symb] = bohr.size() - 1;
            }
            num = bohr[num].vertices[symb];
        }
        bohr[num].ifStr = true;
        pattern.push_back(str);
        bohr[num].patternCount.push_back(pattern.size() - 1);
    }


    int getSuffLink(int vertex) {
        if (bohr[vertex].suffLink == -1) {
            if (vertex == 0 || bohr[vertex].parent == 0) {
                bohr[vertex].suffLink = 0;
            } else {
                bohr[vertex].suffLink = getAutoMove(getSuffLink(bohr[vertex].parent), bohr[vertex].symbol);
            }
        }

        return bohr[vertex].suffLink;
    }

    int getAutoMove(int vertex, char symb) {
        if (bohr[vertex].autoMove[symb] == -1)
            if (bohr[vertex].vertices[symb] != -1)
                bohr[vertex].autoMove[symb] = bohr[vertex].vertices[symb];
            else if (vertex == 0)
                bohr[vertex].autoMove[symb] = 0;
            else
                bohr[vertex].autoMove[symb] = getAutoMove(getSuffLink(vertex), symb);
        return bohr[vertex].autoMove[symb];
    }

    void check(int vertex, int index, vector<int> &count, vector<int> &length) {
        for (int u = vertex; u != 0; u = getSuffLink(u)) {
            if (bohr[u].ifStr) {
                for (auto &b : bohr[u].patternCount) {
                    if (index - length[b] < count.size()) {
                        count[index - length[b]]++;
                    }
                }

            }
        }
    }

    void startSearch(const string &s, vector<int> &count, vector<int> &length) {
        int u = 0;
        for (int i = 0; i < s.length(); i++) {
            u = getAutoMove(u, s[i] - 'A');
            check(u, i + 1, count, length);
        }
    }

    vector<int> dividePattern(stringstream &patterns, char j, vector<string> &pattern) {
        vector<int> length;
        int len = 0;
        string tmp;
        while (getline(patterns, tmp, j)) {
            if (tmp.size() > 0) {
                len += tmp.size();
                length.push_back(len);
                addStringToTrie(tmp, pattern);
            }
            len++;
        }

        return length;
    }


    void printAnswer(vector<int> &count, string mainTextSize, vector<string> patSize) {
        for (int i = 0; i < mainTextSize.size(); i++) {
            if (count[i] == patSize.size())
                cout << i + 1 << endl;
        }
    }

public:
    AK() {
        bohr.push_back(makeTrie(0, -1));
    }

    void run() {
        string mainText;
        string neededPattern;
        char j;
        cin >> mainText;
        cin >> neededPattern;
        cin >> j;
        stringstream ssPatern(neededPattern);
        vector<int> count(mainText.size());
        vector<string> patt;
        vector<int> length = dividePattern(ssPatern, j, patt);
        startSearch(mainText, count, length);
        printAnswer(count, mainText, patt);
    }
};

int main() {
    AK k;
    k.run();

    return 0;
}