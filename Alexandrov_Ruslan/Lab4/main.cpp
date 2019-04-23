#include <iostream>
#include <fstream>

using namespace std;

void getLpsArray(string pat, int patLength, int *lps) {
    // length of the previous longest prefix suffix
    int len = 0;
    int i = 1;
    // always 0
    lps[0] = 0;
    while (i < patLength) {
        if (pat[i] == pat[len]) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = len;
                i++;
            }
        }
    }
}

string getKmp(string pat, string txt) {
    string answer;
    int patLength = pat.length();
    int txtLength = txt.length();
    int* lps = new int[pat.size()];
    int j = 0;

    getLpsArray(pat, patLength, lps);
    int i = 0;
    while (i < txtLength) {
        if (pat[j] == txt[i]) {
            j++;
            i++;
        }
        if (j == patLength) {
            string f = std::to_string(i - j) + ",";
            answer.append(f);
            j = lps[j - 1];
        } else if (pat[j] != txt[i]) {
            if (j != 0) {
                j = lps[j - 1];
            } else {
                i += 1;
            }
        }
    }
    delete[] lps;

    return answer;
}


int shift(string pat, string txt) {
    int answer = -1;
    if (pat.length() != txt.length()) {
        return answer;
    }
    string doublePatText = pat + pat;
    int* lps = new int[pat.size()];
    getLpsArray(pat, pat.size(), lps);
    int index = 0;
    for (int i = 0; i < doublePatText.size(); i++) {
        while (index > 0 && txt[index] != doublePatText[i]) {
            index = lps[index - 1];
        }
        if (txt[index] == doublePatText[i]) {
            index++;
        }
        if (index == txt.size()) {
            answer = (i + 1 - txt.length());
            break;
        }
    }
    delete[] lps;

    return answer;
}

int main() {
    string p;
    string t;
    cin >> p >> t;
  //  cout << shift(p, t) << endl;
    string answer = getKmp(p, t);
    if (answer.length() == 0) {
        cout << "-1" << endl;
    } else {
        for (int i = 0; i < answer.length() - 1; i++) {
            cout << answer[i];
        }
        cout << endl;
    }
    return 0;
}