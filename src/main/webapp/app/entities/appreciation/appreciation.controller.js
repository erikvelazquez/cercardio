(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AppreciationController', AppreciationController);

    AppreciationController.$inject = ['Appreciation', 'AppreciationSearch'];

    function AppreciationController(Appreciation, AppreciationSearch) {

        var vm = this;

        vm.appreciations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Appreciation.query(function(result) {
                vm.appreciations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AppreciationSearch.query({query: vm.searchQuery}, function(result) {
                vm.appreciations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
