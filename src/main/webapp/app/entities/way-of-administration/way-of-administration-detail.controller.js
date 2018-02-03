(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Way_of_AdministrationDetailController', Way_of_AdministrationDetailController);

    Way_of_AdministrationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Way_of_Administration'];

    function Way_of_AdministrationDetailController($scope, $rootScope, $stateParams, previousState, entity, Way_of_Administration) {
        var vm = this;

        vm.way_of_Administration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:way_of_AdministrationUpdate', function(event, result) {
            vm.way_of_Administration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
