<!doctype html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${mailTitle!""}</title>
    <#include "/partials/email_styles.ftlh">
</head>
<body>
<table class="outer-table" role="presentation" width="100%">
    <tr>
        <td align="center" style="padding: 32px 16px;">
            <table class="container" role="presentation" width="100%">
                <#include "/partials/header.ftlh">

                <tr>
                    <td class="content">
                        <#include contentTemplate>
                    </td>
                </tr>

                <tr>
                    <td>
                        <div class="divider"></div>
                    </td>
                </tr>

                <#include "/partials/footer.ftlh">
            </table>
        </td>
    </tr>
</table>
</body>
</html>