(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocioeconomicLevelController', SocioeconomicLevelController);

    SocioeconomicLevelController.$inject = ['SocioeconomicLevel', 'SocioeconomicLevelSearch'];

    function SocioeconomicLevelController(SocioeconomicLevel, SocioeconomicLevelSearch) {

        var vm = this;

        vm.socioeconomicLevels = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            SocioeconomicLevel.query(function(result) {
                vm.socioeconomicLevels = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SocioeconomicLevelSearch.query({query: vm.searchQuery}, function(result) {
                vm.socioeconomicLevels = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
