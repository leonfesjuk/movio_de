<#-- @ftlvariable name="link" type="String" -->
<!doctype html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bestätigung Ihrer Registrierung</title>
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
                        <h1>Bitte bestätigen Sie Ihre E-Mail-Adresse</h1>
                        <p>Ein letzter Schritt, damit Ihr Konto vollständig aktiviert wird.</p>
                    </td>
                </tr>
                <tr>
                    <td class="content">
                        <h2>Willkommen bei Movio!</h2>
                        <p>Vielen Dank für Ihre Registrierung bei <strong>Movio</strong> – Ihrer modernen Plattform für effiziente digitale Prozesse.</p>

                        <div class="highlight-box">
                            <p style="margin: 0;">
                                Um Ihre Registrierung abzuschließen und Ihr Konto zu aktivieren, bestätigen Sie bitte Ihre E-Mail-Adresse über den folgenden Button.
                            </p>
                        </div>

                        <div class="button-wrap">
                            <a href="${link}" class="button">E-Mail bestätigen</a>
                        </div>

                        <p>Falls der Button nicht funktionieren sollte, verwenden Sie bitte den folgenden Link und fügen ihn in die Adresszeile Ihres Browsers ein:</p>

                        <div class="link-box">
                            <a href="${link}">${link}</a>
                        </div>

                        <p style="margin-top: 22px;">
                            Wenn Sie diese Registrierung nicht selbst angefordert haben, können Sie diese Nachricht einfach ignorieren.
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
