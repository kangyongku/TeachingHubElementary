/*************************************************************
 *
 *  MathJax/jax/output/SVG/fonts/TeX/svg/AMS/Regular/Latin1Supplement.js
 *
 *  Copyright (c) 2011-2013 The MathJax Consortium
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

MathJax.Hub.Insert(
  MathJax.OutputJax.SVG.FONTDATA.FONTS['MathJax_AMS'],
  {
    // YEN SIGN
    0xA5: [683,0,750,11,738,'515 0Q494 3 374 3Q256 3 235 0H224V46H257Q316 47 324 58Q327 62 327 137V213H133Q121 213 113 213T97 213T86 213T78 213T73 214T70 215T69 216T68 218T67 220Q64 225 66 231T73 240Q76 242 202 242H327V273L247 407H115Q81 407 75 408T67 414Q64 419 66 425T73 434Q76 436 153 436Q228 436 228 437Q227 440 173 530T115 623Q101 637 31 637H11V683H20Q66 681 153 681Q169 681 202 681T262 682L288 683H298V637H280Q230 636 230 621Q230 619 250 584Q255 576 264 561T286 526T305 494L340 437L403 436H467L513 514Q564 596 564 605Q564 608 560 616Q550 634 517 637H508V683H516Q531 680 633 680Q722 680 731 683H738V637H723Q644 632 617 595Q614 591 568 515T521 437T597 436T676 434Q681 432 683 426T682 414T671 409T589 407H503L422 273V242H547Q673 242 676 240Q681 238 683 232T682 220Q682 219 682 218T681 217T679 216T677 215T672 214T664 213T652 213T637 213T616 213H422V139V87Q422 64 425 58T441 49Q456 46 503 46H525V0H515ZM449 406Q449 407 403 407Q358 407 358 406L370 387Q381 368 392 350L404 331Q447 404 449 406'],

    // REGISTERED SIGN
    0xAE: [709,176,947,32,915,'915 266Q915 140 852 38T689 -120T474 -175Q312 -175 188 -71T38 190Q32 220 32 266V287Q32 345 57 416T129 545Q192 624 282 666T464 709Q513 709 522 708Q599 698 665 666T776 590T853 493T900 387T915 287V266ZM875 285Q875 339 853 399T789 517T676 616T519 668Q510 669 465 669Q380 669 299 630T155 514T77 336Q72 312 72 285V266V256Q72 123 163 11Q290 -135 474 -135Q614 -135 727 -46Q875 81 875 266V285ZM276 457Q275 458 274 460T272 463T270 465T267 467T264 469T258 471T252 472T243 473T232 474T218 474H204V514H335Q477 514 499 510Q560 502 610 467T661 375Q661 362 658 350T648 327T635 308T618 292T601 280T583 269T568 262T554 256L547 253Q548 252 556 247T570 237T586 223T602 202T614 174Q616 169 626 123T638 72Q652 23 683 23Q715 23 720 68Q721 78 724 81T740 84T756 82T760 70Q760 47 747 25T715 -7Q700 -14 673 -14Q672 -14 662 -14T643 -12T619 -7T593 2T568 16T547 37T534 67Q531 80 531 97Q531 103 531 116T532 136Q532 218 472 236Q466 238 413 239H360V148L361 58Q366 47 375 44T418 40H432V0H424Q409 3 318 3T212 0H204V40H218Q242 40 253 42T268 47T276 58V457ZM376 473Q365 471 363 464T360 430V366V276H416Q421 276 434 276T453 276T469 277T486 279T501 282T517 287T529 294T542 305Q561 324 561 375Q561 424 545 444T482 472Q478 473 427 474Q415 474 403 474T384 474L376 473'],

    // LATIN SMALL LETTER ETH
    0xF0: [749,21,556,42,509,'75 566V604Q75 624 79 629T102 635Q124 635 127 629T131 588L133 550L191 588L249 628L231 635Q176 654 124 657Q116 657 106 658L95 659Q94 661 94 687T95 715Q99 717 113 717Q195 717 282 679L309 668L331 681Q351 697 391 721Q428 748 435 748Q437 749 446 749Q470 749 473 746Q478 744 478 681V621Q466 615 456 615Q435 615 424 624L422 661V699L382 675L344 648Q353 639 366 630Q480 538 504 413Q509 393 509 333V313Q509 284 507 257T495 184T466 102T413 33T329 -16Q311 -21 275 -21Q226 -21 195 -10Q150 7 110 50T53 141Q42 179 42 227Q42 332 101 403T245 474Q282 474 314 461T359 436T380 415Q386 405 389 408Q389 426 378 475Q368 505 355 529T329 567T306 590T288 603L282 606L120 501Q116 500 102 500Q84 500 75 506V566ZM388 225Q388 376 309 410Q299 416 273 419Q216 419 191 390Q174 371 168 342T162 218Q162 112 184 79Q212 39 273 39Q312 39 342 62T380 121Q388 159 388 225']
  }
);

MathJax.Ajax.loadComplete(MathJax.OutputJax.SVG.fontDir+"/AMS/Regular/Latin1Supplement.js");
