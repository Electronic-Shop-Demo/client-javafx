$Targets = './logs', './settings', './out'

function GetTree($Path, $Include = '*')
{
    @(Get-Item $Path -Include $Include -Force) +
        (Get-ChildItem $Path -Recurse -Include $Include -Force) |
        sort pspath -Descending -unique
}

function RemoveTree($Path, $Include = '*')
{
    GetTree $Path $Include | Remove-Item -force -recurse
}

function ZygoteInit
{
    foreach ($item in $Targets)
    {
        RemoveTree $item
    }
}

ZygoteInit
