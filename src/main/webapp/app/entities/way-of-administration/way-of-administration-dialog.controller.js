(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Way_of_AdministrationDialogController', Way_of_AdministrationDialogController);

    Way_of_AdministrationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Way_of_Administration'];

    function Way_of_AdministrationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Way_of_Administration) {
        var vm = this;

        vm.way_of_Administration = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.way_of_Administration.id !== null) {
                Way_of_Administration.update(vm.way_of_Administration, onSaveSuccess, onSaveError);
            } else {
                Way_of_Administration.save(vm.way_of_Administration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:way_of_AdministrationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
