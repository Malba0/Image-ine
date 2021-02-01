# Image-ine
POC Fetch random image from PicsumPhotos
and display in list. 
- Re-arranged by long-press and dragging.
- Remove by swiping left or right.

# Architecture
Clean MVVM with UseCase for business logic.
Models are not passed up the dependency chain.

View <(Uio) ViewModel <(Bo) UseCase <(Dto) Repositories <(Dao) ServiceApi

Some DI by dagger

# Icon
<div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik
</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

# License
Copyright (C) 2021  NAW

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.