#include <iostream>
#include <vector>
#include <string>
#include <cstring>

using namespace std;

class AK {
    static const int defaultLength = 64;

    struct Trie {
        int vertices[defaultLength];
        int patternsCount;
        bool ifStr;
        int suffLink;
        int autoMove[defaultLength];
        int parent;
        char symbol;
    };
    vector<Trie> trie;
    vector<string> pattern;

    void initArrays(int *array) {
        for (int i = 0; i < defaultLength; i++) {
            array[i] = -1;
        }
    }

    int getSuffLink(int vertex) {
        if (trie[vertex].suffLink == -1) {
            if (vertex == 0 || trie[vertex].parent == 0) {
                trie[vertex].suffLink = 0;
            } else {
                trie[vertex].suffLink = getAutoMove(getSuffLink(trie[vertex].parent), trie[vertex].symbol);
            }
        }

        return trie[vertex].suffLink;
    }

    int getAutoMove(int vertex, char symb) {
        if (trie[vertex].autoMove[symb] == -1) {
            if (trie[vertex].vertices[symb] != -1) {
                trie[vertex].autoMove[symb] = trie[vertex].vertices[symb];
            } else if (vertex == 0) {
                trie[vertex].autoMove[symb] = 0;
            } else {
                trie[vertex].autoMove[symb] = getAutoMove(getSuffLink(vertex), symb);
            }
        }

        return trie[vertex].autoMove[symb];
    }


    void checkAllSuffLinks(int vertex, int symb) {
        for (int i = vertex; i != 0; i = getSuffLink(i)) {
            if (trie[i].ifStr) {
                cout << symb - pattern[trie[i].patternsCount].length() + 1 << " " << trie[i].patternsCount + 1 << endl;
            }
        }
    }

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

    void addStringToTrie(string &str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str[i] - 'A';
            if (trie[num].vertices[ch] == -1) {
                trie.push_back(makeTrie(num, ch));
                trie[num].vertices[ch] = trie.size() - 1;
            }
            num = trie[num].vertices[ch];
        }

        trie[num].ifStr = true;
        pattern.push_back(str);
        trie[num].patternsCount = pattern.size() - 1;
    }


    void startSearch(string &str) {
        int v = 0;
        for (int i = 0; i < str.length(); i++) {
            v = getAutoMove(v, str[i] - 'A');
            checkAllSuffLinks(v, i + 1);
        }
    }

public:
    AK() {
        trie.push_back(makeTrie(0, -1));
    }

    void run() {
        string mainText;
        int totalPatterns;
        cin >> mainText;
        cin >> totalPatterns;
        string pattern;
        for (int i = 0; i < totalPatterns; i++) {
            cin >> pattern;
            addStringToTrie(pattern);
        }

        startSearch(mainText);
    }
};

int main() {
    AK k;
    k.run();

    return 0;
}