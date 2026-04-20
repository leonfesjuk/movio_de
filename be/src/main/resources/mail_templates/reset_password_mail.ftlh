<#-- @ftlvariable name="link" type="String" -->
<!doctype html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Passwort zurücksetzen</title>
    <#include "partials/email_styles.ftlh">
</head>
<body>
<table class="outer-table" role="presentation" width="100%">
    <tr>
        <td align="center" style="padding: 32px 16px;">
            <table class="container" role="presentation" width="100%">
                <tr>
                    <td class="header">
                        <div class="brand">Movio</div>
                        <h1>Passwort zurücksetzen</h1>
                        <p>Ein sicherer Link, um den Zugriff auf Ihr Konto wiederherzustellen.</p>
                    </td>
                </tr>
                <tr>
                    <td class="content">
                        <h2>Sie haben eine Zurücksetzung des Passworts angefordert</h2>
                        <p>Wir haben eine Anfrage erhalten, das Passwort für Ihr <strong>Movio</strong>-Konto zurückzusetzen.</p>

                        <div class="highlight-box">
                            <p style="margin: 0;">
                                Klicken Sie auf den folgenden Button, um ein neues Passwort festzulegen.
                                Der Link ist aus Sicherheitsgründen nur für eine begrenzte Zeit gültig.
                            </p>
                        </div>

                        <div class="button-wrap">
                            <a href="${link}" class="button">Passwort zurücksetzen</a>
                        </div>

                        <p>Falls der Button nicht funktionieren sollte, verwenden Sie bitte den folgenden Link und fügen ihn in die Adresszeile Ihres Browsers ein:</p>

                        <div class="link-box">
                            <a href="${link}">${link}</a>
                        </div>

                        <p style="margin-top: 22px;">
                            Falls Sie diese Anfrage nicht selbst gestellt haben, können Sie diese Nachricht ignorieren. Ihr Passwort bleibt unverändert.
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="divider"></div>
                    </td>
                </tr>
                <tr>
                    <td class="footer">
                        <p style="margin: 0 0 8px;">© 2026 Movio (movio.com.ua). Alle Rechte vorbehalten.</p>
                        <p style="margin: 0;">
                            Diese E-Mail wurde automatisch generiert. Bitte antworten Sie nicht direkt auf diese Nachricht.
                        </p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
