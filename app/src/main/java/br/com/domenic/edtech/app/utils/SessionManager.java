package br.com.domenic.edtech.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Professor;

public class SessionManager {
    private static final String PREF_NAME = "EdTechSession";
    private static final String KEY_USER_TYPE = "userType";
    private static final String KEY_USER_ID = "userId";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveAlunoSession(Aluno aluno) {
        editor.putString(KEY_USER_TYPE, "aluno");
        editor.putInt(KEY_USER_ID, aluno.getIdAluno());
        editor.apply();
    }

    public void saveProfessorSession(Professor professor) {
        editor.putString(KEY_USER_TYPE, "professor");
        editor.putInt(KEY_USER_ID, professor.getIdProfessor());
        editor.apply();
    }

    public String getUserType() {
        return preferences.getString(KEY_USER_TYPE, null);
    }

    public int getUserId() {
        return preferences.getInt(KEY_USER_ID, -1);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}