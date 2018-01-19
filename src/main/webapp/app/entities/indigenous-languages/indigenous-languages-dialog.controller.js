(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Indigenous_LanguagesDialogController', Indigenous_LanguagesDialogController);

    Indigenous_LanguagesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Indigenous_Languages'];

    function Indigenous_LanguagesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Indigenous_Languages) {
        var vm = this;

        vm.indigenous_Languages = entity;
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
            if (vm.indigenous_Languages.id !== null) {
                Indigenous_Languages.update(vm.indigenous_Languages, onSaveSuccess, onSaveError);
            } else {
                Indigenous_Languages.save(vm.indigenous_Languages, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:indigenous_LanguagesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
