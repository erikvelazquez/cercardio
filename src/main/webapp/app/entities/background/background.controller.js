(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BackgroundController', BackgroundController);

    BackgroundController.$inject = ['Background', 'BackgroundSearch'];

    function BackgroundController(Background, BackgroundSearch) {

        var vm = this;

        vm.backgrounds = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Background.query(function(result) {
                vm.backgrounds = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            BackgroundSearch.query({query: vm.searchQuery}, function(result) {
                vm.backgrounds = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
